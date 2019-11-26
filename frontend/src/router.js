import Vue from 'vue'
import Router from 'vue-router'
import Home from './components/general/Home.vue';
import About from './components/general/About.vue';
import Orders from './components/ordering/Order.vue';

Vue.use(Router)

export default new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home
    },
    {
      path: '/about',
      name: 'about',
      component: About
    },
    {
      path: '/orders',
      name: 'orders',
      component: Orders
    }
  ]
})
