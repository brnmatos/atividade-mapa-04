import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import LoginView from '../views/LoginView.vue'
import ProdutosView from '../views/ProdutosView.vue'
import AnunciarView from '@/views/AnunciarView.vue'
import ProdutoView from '@/views/ProdutoView.vue'

const routes = [
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/homeAutenticado/:id',
    name: 'homeAutenticado',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/produtos/:id',
    name: '/produtos', 
    component: ProdutosView
  },
  {
    path: '/anunciar/:id',
    name: '/anunciar', 
    component: AnunciarView
  },
  {
    path: '/produto-visualizar/:id/:idProduto',
    name: '/produto-visualizar', 
    component: ProdutoView
  },
  {
    path: '/contato/:id',
    name: '/contato', 
    component: () => import(/* webpackChunkName: "contato" */ '../views/Contato.vue')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
