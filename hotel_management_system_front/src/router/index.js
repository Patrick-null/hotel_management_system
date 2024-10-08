import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import GuestView from '@/views/GuestView.vue'
import OrdersView from '@/views/OrdersView.vue'

import IndexView from '@/views/IndexView.vue'

import RoomView from '@/views/RoomView.vue'

import FacilityView from '@/views/FacilityView.vue'
import LoginView from '@/views/LoginView.vue'
import LoginTwoView from '@/views/LoginUserView.vue'
import IndexViewUser from '@/views/IndexUserView.vue'
import IndexUserView from '@/views/IndexUserView.vue'
import LoginUserView from '@/views/LoginUserView.vue'
import HomeUserView from '@/views/HomeUserView.vue'
import OrdersUserView from '@/views/OrdersUserView.vue'
import BookingView from '@/views/BookingView.vue'
import { useTokenStore } from '@/stores/token'
import UserView from '@/views/UserView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/admin',
      name: 'home',
      component: HomeView,
      redirect:'/index',
      children:[
        {
          path:'/userInfo',
          component:UserView
        },{
          path:'/guest',
          component:GuestView
        },{
          path:'/orders',
          component:OrdersView
        },{
          path:'/index',
          component:IndexView
        },{
          path:'/room',
          component:RoomView
        },{
          path:'/facility',
          component:FacilityView
        }
      ]
    },
    {
      path: '/login',
      name: 'login',
      component:LoginView     
    },
    {
      path: '/loginUser',
      name: 'loginUser',
      component:LoginUserView
    },
    {
      path: '/user',
      name: 'homeUser',
      component: HomeUserView,
      redirect:'/indexUser',
      children:[
        {
          path:'/booking',
          component:BookingView
        },{
          path:'/indexUser',
          component:IndexUserView
        },{
          path:'/ordersUser',
          component:OrdersUserView,
        }
      ]
    },
  ]
})

//导航守卫
router.beforeEach((to,from)=>{
  //to - 要访问的位置
  //from - 起始位置
  if(to.path == '/login'|| to.path == '/loginUser'){
    return true;
  }else{
    const tokenStore = useTokenStore()
    //判断是否sessionStorage中是否有token
    if(!tokenStore.tokenStr){
      return '/login'
    }else{
      return true;
    }
  }
})

export default router
