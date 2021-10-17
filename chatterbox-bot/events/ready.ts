module.exports = {
    name: "ready",
    once: true,
    // @ts-ignore
    execute (client) {
        console.log(`Chatterbox Bot is ready, logged in as "${client.user.tag}"`);
    }
};
