FROM node:22-alpine as build
COPY . /src
WORKDIR /src
RUN npm ci && npm run build

FROM nginx:latest
RUN rm /etc/nginx/conf.d/default.conf
COPY nginx.conf /etc/nginx/conf.d
# Copy production build to server folder
COPY --from=build /src/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]