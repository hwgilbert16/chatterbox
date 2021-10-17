FROM node:16

WORKDIR /usr/src/app

COPY package*.json ./

RUN npm install --production
RUN npx tsc --build --clean

COPY . .

EXPOSE 3000

CMD ["npm", "start"]