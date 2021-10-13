declare global {
    namespace NodeJS {
        interface ProcessEnv {
            DISCORD_TOKEN: string,
            AUTH_TOKEN: string,
            CHANNEL_ID: string,
            GUILD_ID: string
        }
    }
}

export {}