import Login from './views/Login.vue'
import Welcome from './views/Welcome'
import NotFound from './views/404.vue'
import Home from './views/Home.vue'
import SysUser from './views/base/SysUser'
import SysLevel from './views/base/SysLevel'
import OperationRec from './views/operation/OperationRecord'
import Report from './views/report/Report'

let routes = [
    {
        path: '/login',
        component: Login,
        name: '',
        hidden: true
    },
    {
        path: '/404',
        component: NotFound,
        name: '',
        hidden: true
    },
    {
        path: '/',
        component: Home,
        name: '',
        iconCls: 'fa fa-address-card',
        leaf: true,//只有一个节点,
        children: [
            { path: '/welcome', component: Welcome,name: '首页'}
        ]
    },
    {
        path: '/',
        component: Home,
        name: '基础管理',
        iconCls: 'fa fa-id-card-o',//图标样式class
        children: [
            { path: '/sys_user', component: SysUser, name: '用户管理' },
            { path: '/sys_level', component: SysLevel, name: '级别管理' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '手术管理',
        iconCls: 'fa fa-id-card-o',
        children: [
            { path: '/operation_rec', component: OperationRec, name: '手术记录' }
        ]
    },
    {
        path: '/',
        component: Home,
        name: '报表管理',
        iconCls: 'fa fa-address-card',
        children: [
            { path: '/report', component: Report, name: '绩效报表' }
        ]
    },
    {
        path: '*',
        hidden: true,
        redirect: { path: '/404' }
    }
];

export default routes;