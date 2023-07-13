// Composables
import {
  createRouter,
  createWebHistory
} from 'vue-router'

const routes = [{
  path: '/',
  component: () => import('@/layout/default/Index.vue'),
  children: [{
    path: '',
    name: 'Home',
    component: () => import( /* webpackChunkName: "home" */ '@/views/Home.vue'),
  }, {
    path: 'd/:id',
    name: 'editor',
    props: true,
    component: () => import( /* webpackChunkName: "home" */ '@/views/Editor.vue'),
  }, {
    path: 'd/create',
    name: 'create',
    component: () => import( /* webpackChunkName: "home" */ '@/views/Editor.vue'),
  }, {
    path: 'tags',
    name: 'tags',
    component: () => import( /* webpackChunkName: "home" */ '@/views/Tags.vue'),
  }],
}]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes,
})

export default router
