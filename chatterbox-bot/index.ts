import {Client, Intents} from "discord.js";
import * as dotenv from "dotenv";
dotenv.config({path: __dirname+'/.env'});
import {Server} from "socket.io";
import * as fs from "fs";

const client = new Client({intents: [Intents.FLAGS.GUILDS, Intents.FLAGS.GUILD_MESSAGES]});
const eventFiles = fs.readdirSync("./events").filter(file => file.endsWith('.js'));

const io: Server = new Server(3000, {});

// Create .env if it doesn't exist
if (!fs.existsSync(__dirname + "/.env")) {
    fs.openSync(__dirname + "/.env", "w");

    fs.appendFileSync(__dirname + "/.env", "DISCORD_TOKEN=\nCHANNEL_ID=\nAUTH_TOKEN=");
}

if (!process.env.DISCORD_TOKEN || !process.env.CHANNEL_ID || !process.env.AUTH_TOKEN) {
    console.error(".env file is not configured! Fill in relevant values before starting bot.");
    process.exit(9);
}

// Check to make sure auth is correct
io.on("connection", (socket) => {
    if (socket.handshake.auth['auth-token'] !== process.env.AUTH_TOKEN) {
        console.log("Invalid auth token, disconnecting");
        socket.disconnect();
        return;
    }

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