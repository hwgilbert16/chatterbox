module.exports = {
    name: 'messageCreate',
    once: false,
    // @ts-ignore
    execute(message, io) {
        if (message.channelId === process.env.CHANNEL_ID && !message.author.bot) {
            //const guild = message.client.guilds.get(message.guildId);
            //const guildMember = message.client.guilds.cache.get(message.author.id);
            //const guildMember = guild.member(message.author);
            //console.log(message.client.guilds.cache.toString());
            //io.emit("message", `<${message.author.username}> ${message.content}`);
            io.emit("message", `<${message.member.displayName}> ${message.content}`);
        }
    }
}