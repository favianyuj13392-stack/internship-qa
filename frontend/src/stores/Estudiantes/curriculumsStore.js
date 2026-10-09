import { defineStore } from 'pinia'
import axios from 'axios'
import RutaApi from '@/assets/rutaApi.js'

export const useCurriculumsStore = defineStore({
    id: 'curriculums',
    state: () => ({
        errorMessageDeleteCurriculum: '',
    }),
    actions:{
        async postCurriculum(data,uuid){
            try{
                const formData = new FormData();
                formData.append('file', data);
                const response = await axios.post(RutaApi + '/estudiante/'+uuid+'/curriculum',formData,{
                    headers: {
                        'Content-Type': 'multipart/form-data',
                    }
                })
                console.log(response)
                if(response.data.code == '200'){
                    return response.data.response
                }else{
                    return null;
                }
            }catch(error){
                console.log(error)
            }
        },
       

        async getCurriculum(uuid){
            try{
                const response = await axios.get(RutaApi + '/estudiante/'+uuid+'/curruculum')
                if(response.data.code == '200'){
                    return response.data.response
                }else{
                    return null;
                }
            }catch(error){
                console.log(error)
            }
        },
        async deleteCurriculum(uuid, curriculumId){
            try{
                const response = await axios.delete(RutaApi + '/estudiante/'+uuid+'/curriculum/'+curriculumId)
                if(response.data.code == '200'){
                    return response.data.response
                }else{
                    this.errorMessageDeleteCurriculum = response.data.errorMessage;
                    console.log(this.errorMessageDeleteCurriculum)
                    return null;
                }
            }catch(error){
                console.log(error)
            }
        }

    }
});