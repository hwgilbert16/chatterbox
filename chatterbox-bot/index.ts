import {Client, Intents} from "discord.js";
import * as dotenv from "dotenv";
dotenv.config({path: __dirname+'/.env'});
import {Server} from "socket.io";
import * as fs from "fs";

const client = new Client({intents: [Intents.FLAGS.GUILDS, Intents.FLAGS.GUILD_MESSAGES]});
const eventFiles = fs.readdirSync("./events").filter(file => file.endsWith('.js'));

const io: Server = new Server(3000, {});

io.on("connection", (socket) => {
    console.log("Client connected");
});

for (const file of eventFiles) {
    const event = require(`./events/${file}`);
    if (event.once) {
        client.once(event.name, (...args) => event.execute(...args, io));
    } else {
        client.on(event.name, (...args) => event.execute(...args, io));
    }
}

client.login(process.env.TOKEN);