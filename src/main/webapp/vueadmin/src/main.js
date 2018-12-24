import Vue from 'vue'
import App from './App'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-default/index.css'
import VueRouter from 'vue-router'
import store from './vuex/store'
import Vuex from 'vuex'
import axios from 'axios';
import routes from './routes'
import util from './common/js/util'
// import Mock from './mock'
// Mock.bootstrap();
import 'font-awesome/css/font-awesome.min.css'
Vue.use(ElementUI)
Vue.use(VueRouter)
Vue.use(Vuex)
// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded;charset=utf-8'
// 添加响应拦截器
axios.interceptors.response.use((response)=> {
    // 对响应数据做点什么
    return response;
}, (error)=> {
    // 对响应错误做点什么
    return Promise.reject(error.response);
});
const router = new VueRouter(
    {
    mode: 'history',
    base:'/pms/',
    routes
  })

router.beforeEach((to, from, next) => {
        let user = util.getLocalStorage("user");
        if (!user&& to.path != '/login') {
            next({ path: '/login' })
        } else if(user&&(to.path=="/login"||to.path=='/')){
            next({ path: '/welcome' })
        }else {
            next();
        }
})
new Vue({
  //el: '#app',
  //template: '<App/>',
  router,
  store,
  //components: { App }
  render: h => h(App)
}).$mount('#app')

