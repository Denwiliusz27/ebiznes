FROM node:18.16.0

RUN mkdir client-React
WORKDIR client-React

COPY . .

RUN npm ci --ignore-scripts

EXPOSE 5173

CMD ["npm", "run", "dev"]
