import {
  loadFonts
} from './webfontloader'
import vuetify from './vuetify'
import pinia from '../store'
import router from '../router'
export default {
  install(app, options) {
    console.log('Installing plugins...', options)
    loadFonts()
    app
      .use(vuetify)
      .use(router)
      .use(pinia)
  }
}
