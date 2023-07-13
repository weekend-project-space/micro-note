import App from './App.vue'
import './styles/index.scss'

import {
  createApp
} from 'vue'

// Plugins
import plugins from '@/plugins'
createApp(App).use(plugins).mount('#app')
