import { defineStore } from 'pinia';
import axios from 'axios';
import RutaApi from '@/assets/rutaApi.js';

export const useFilesStore = defineStore({
    id: 'files',
    state: () => ({
        fileData: [],
        porcentajeCarga: 0,
        link: '',
    }),
    actions: {
        async uploadFile(file) {
            try {
                const formData = new FormData();
                formData.append('file', file);
                console.log(file);
                console.log(formData);
           
               
        
                const response = await axios.post(RutaApi + '/files/upload', formData, {
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    },
                    onUploadProgress: progressEvent => {
                        this.porcentajeCarga = Math.round((progressEvent.loaded * 100) / progressEvent.total);
                        console.log(this.porcentajeCarga);
                    }
                });
        
                console.log(response);
        
                if (response.status === 200) {
                    this.link = response.data.response.url;
                    return true;
                } else {
                    console.error('La carga del archivo no fue exitosa. Estado:', response.status);
                    return false;
                }
            } catch (error) {
                console.error('Error en la carga del archivo:', error);
                return false;
            }
        }
    }
});
