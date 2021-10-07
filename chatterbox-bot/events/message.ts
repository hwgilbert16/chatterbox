module.exports = {
    name: 'messageCreate',
    once: false,
    // @ts-ignore
    execute(message, io) {
        console.log(message.content);
        console.log(io._connectTimeout);
    }
}