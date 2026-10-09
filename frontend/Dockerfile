FROM nginx:1.22.0-alpine

# Elimina la configuración predeterminada de Nginx
RUN rm -rf /usr/share/nginx/html/*

# Copia los archivos del directorio dist de tu proyecto a la ubicación de Nginx
COPY ./dist /usr/share/nginx/html/

# Copia el archivo nginx.conf al lugar correcto
COPY nginx.conf /etc/nginx/conf.d/default.conf

# Exponer el puerto 80 para que Nginx pueda servir la aplicación
EXPOSE 80

# Comando para iniciar Nginx en el modo foreground
CMD ["nginx", "-g", "daemon off;"]
