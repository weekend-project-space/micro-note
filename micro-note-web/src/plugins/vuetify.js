// Styles
import '@mdi/font/css/materialdesignicons.css'
import 'vuetify/styles'
import themes from '@/themes'
// Composables
import {
  createVuetify
} from 'vuetify'

// https://vuetifyjs.com/en/introduction/why-vuetify/#feature-guides
export default createVuetify({
  theme: {
    themes,
    defaultTheme: 'light'
  },
})
