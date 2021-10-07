declare global {
    namespace NodeJS {
        interface ProcessEnv {
            TOKEN: string,
            CHANNEL_ID: string
        }
    }
}

export {}