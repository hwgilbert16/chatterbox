import { Client, Intents, MessageEmbed } from "discord.js";
import * as dotenv from "dotenv";
import { Server } from "socket.io";
import * as fs from "fs";
import * as path from "path";

dotenv.config({ path: path.join(__dirname, "/.env") });

const client = new Client({ intents: [Intents.FLAGS.GUILDS, Intents.FLAGS.GUILD_MESSAGES] });
const eventFiles = fs.readdirSync("./events").filter(file => file.endsWith(".js"));

const io: Server = new Server(3000, {});

// Create .env if it doesn't exist
if (!fs.existsSync(path.join(__dirname, "/.env"))) {
    fs.openSync(path.join(__dirname, "/.env"), "w");

    fs.appendFileSync(path.join(__dirname, "/.env"), "DISCORD_TOKEN=\nCHANNEL_ID=\nGUILD_ID=\nAUTH_TOKEN=\nLOCK_ROLE_ID\nLOCK_CHANNEL_WHEN_OFFLINE");
}

if (!process.env.DISCORD_TOKEN || !process.env.CHANNEL_ID || !process.env.AUTH_TOKEN || !process.env.GUILD_ID || !process.env.LOCK_ROLE_ID || !process.env.LOCK_CHANNEL_WHEN_OFFLINE) {
    console.error(".env file is not configured! Fill in relevant values before starting bot.");
    process.exit(9);
}

// Check to make sure auth is correct
io.on("connection", async (socket) => {
    if (socket.handshake.auth["auth-token"] !== process.env.AUTH_TOKEN) {
        console.log("Invalid auth token, disconnecting");
        socket.disconnect();
        return;
    }

    socket.on("getId", async (data) => {
        const guild: any = await client.guilds.cache.get(process.env.GUILD_ID);

        const guildMember = await guild.members.fetch({
            query: data,
            limit: 1
        });

        // First key of guild members return the ID
        const userID: number = guildMember.firstKey();

        if (!userID) {
            socket.emit("returnId", JSON.stringify({ username: data, id: null }));
        } else {
            // Preventative against weirdness of spaced usernames
            // Sets ID to null if the username from Discord does not match supplied username from the server
            if (guildMember.get(guildMember.firstKey()).user.username !== data) {
                socket.emit("returnId", JSON.stringify({ username: data, id: null }));
                return;
            }
            socket.emit("returnId", JSON.stringify({ username: data, id: userID }));
        }
    });

    socket.on("disconnect", async () => {
        const guild: any = await client.guilds.cache.get(process.env.GUILD_ID);

        // @ts-ignore
        // get the role that will be prevented from sending messages when the server is not connected
        const role = guild.roles.cache.find(r => r.id === guild.roles.everyone.id);

        // get the channel that messages are sent in
        const channel: any = client.channels.cache.get(process.env.CHANNEL_ID);

        const offlineEmbed = new MessageEmbed()
            .setColor("#ff0000")
            .setDescription("The server is now offline");

        channel.send({ embeds: [offlineEmbed] });

        if (process.env.LOCK_CHANNEL_WHEN_OFFLINE === "true") {
            // prevent specified role from sending messages when the server disconnects
            channel.permissionOverwrites.create(role, {
                SEND_MESSAGES: false
            });
        }

        console.log("Client disconnected");
    });

    const guild: any = await client.guilds.cache.get(process.env.GUILD_ID);

    // @ts-ignore
    const role = guild.roles.cache.find(r => r.id === guild.roles.everyone.id);
    const channel: any = client.channels.cache.get(process.env.CHANNEL_ID);

    const onlineEmbed = new MessageEmbed()
        .setColor("#00BF00")
        .setDescription("The server is now online");

    channel.send({ embeds: [onlineEmbed] });

    channel.permissionOverwrites.create(role, {
        SEND_MESSAGES: true
    });

    console.log("Client connected");
});

// Setup events
for (const file of eventFiles) {
    const event = require(`./events/${file}`);
    if (event.once) {
        client.once(event.name, (...args) => event.execute(...args, io));
    } else {
        client.on(event.name, (...args) => event.execute(...args, io));
    }
}

client.login(process.env.DISCORD_TOKEN);
