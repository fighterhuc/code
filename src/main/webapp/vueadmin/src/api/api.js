import axios from 'axios';
import querystring from 'querystring'
import Vue from 'vue'
let base = '/pms/mvc/';

/**
 * post提交
 * @param url
 * @param params
 * @returns {Promise<any>}
 */
export const httpPost = (url,params,obj)=> {
    return axios.post(`${base}`+url, querystring.stringify(params)).then(res => res.data).catch(error=>{
        var status = error.status;
        if(status==10086){
            Vue.prototype.$confirm ('登陆超时，请重新登陆', '提示', {
            }).then(() => {
                window.localStorage.removeItem("user");
                obj.$router.push('/login');
            }).catch(() => {
            });
        }
    });
}