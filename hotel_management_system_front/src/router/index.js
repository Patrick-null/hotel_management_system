import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import GuestView from '@/views/GuestView.vue'
import OrdersView from '@/views/OrdersView.vue'
import EmployeeView from '@/views/EmployeeView.vue'
import IndexView from '@/views/IndexView.vue'
import DeptView from '@/views/DeptView.vue'
import RoomView from '@/views/RoomView.vue'
import MemberView from '@/views/MemberView.vue'
import FacilityView from '@/views/FacilityView.vue'
import LoginView from '@/views/LoginView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'home',
      component: HomeView,
      children:[
        {
          path:'/guest',
          component:GuestView
        },{
          path:'/orders',
          component:OrdersView
        },{
          path:'/employee',
          component:EmployeeView
        },{
          path:'/index',
          component:IndexView
        },{
          path:'/dept',
          component:DeptView
        },{
          path:'/room',
          component:RoomView
        },{
          path:'/member',
          component:MemberView
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
   
   
      
    }
  ]
})

export default router
