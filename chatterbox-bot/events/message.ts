module.exports = {
    name: 'messageCreate',
    once: false,
    // @ts-ignore
    execute(message, io) {
        if (message.channelId === process.env.CHANNEL_ID && !message.author.bot) {
            let finalMessage: string = message.content;

            // Count number of mentions in the message
            const expression = /<@.[0-9]+>/g;

            let match: RegExpExecArray | null;

            while ((match = expression.exec(message.content)) != null) {
                // Get user ID, including < signs
                const userIdFull = message.content.substring(match.index, match.index + 22);

                // Get standalone user ID, only the numbers
                const userId = message.content.substring(match.index + 3, match.index + 21);

                // Get username of the mentioned user
                const username = message.client.users.cache.get(userId).username;

                // Replace the full user ID with the username and an @ symbol
                finalMessage = finalMessage.replaceAll(userIdFull, "@" + username);
            }

            // Send the complete message to the plugin to be sent in the chat
            io.emit("message", `<${message.member.displayName}> ${finalMessage}`);
        }
    }
}