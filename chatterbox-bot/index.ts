import {Client, Intents} from "discord.js";
import * as dotenv from "dotenv";
dotenv.config({path: __dirname+'/.env'});
import {Server} from "socket.io";

const client = new Client({intents: [Intents.FLAGS.GUILDS]});

const io: Server = new Server(3000, {});

client.on('ready', () => {
    io.on("connection", (socket) => {
        console.log("Client connected");
    })
})

client.login(process.env.TOKEN);