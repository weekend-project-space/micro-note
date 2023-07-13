<template>
  <v-theme-provider :theme="theme" with-background class="pa">
    <v-layout>
      <v-app-bar :elevation="0">
        <template v-slot:prepend>
          <v-app-bar-nav-icon
            icon="mdi:mdi-menu"
            @click="drawer = !drawer"
          ></v-app-bar-nav-icon>
          <v-toolbar-title class="ma-2">
            <div class="logo-warp">
              <img class="logo mr-2" src="/logo.png" alt="" />
              <span class="logo-title">Micro Note</span>
            </div></v-toolbar-title
          >
        </template>

        <div class="search" v-if="!mobile">
          <div class="search-btn" @click="router.push('/setup')">
            <svg
              focusable="false"
              height="24px"
              viewBox="0 0 24 24"
              width="24px"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M20.49,19l-5.73-5.73C15.53,12.2,16,10.91,16,9.5C16,5.91,13.09,3,9.5,3S3,5.91,3,9.5C3,13.09,5.91,16,9.5,16 c1.41,0,2.7-0.47,3.77-1.24L19,20.49L20.49,19z M5,9.5C5,7.01,7.01,5,9.5,5S14,7.01,14,9.5S11.99,14,9.5,14S5,11.99,5,9.5z"
              ></path>
              <path d="M0,0h24v24H0V0z" fill="none"></path>
            </svg>
          </div>
          <input
            type="text"
            placeholder="搜索"
            v-model="value"
            @keydown.enter="$router.push('/?name=' + value)"
          />
        </div>

        <template v-slot:append>
          <v-menu>
            <template v-slot:activator="{ props }">
              <v-btn icon="mdi:mdi-apps" v-bind="props"></v-btn>
            </template>

            <v-card class="apps">
              <v-btn class="app" variant="text" href="https://note.01234.fun">
                <img src="/logo.png" alt="" />
                <div>note</div>
              </v-btn>
              <v-btn class="app" variant="text" href="http://01234.fun">
                <img src="http://01234.fun/favicon.ico" alt="" />
                <div>tv</div>
              </v-btn>
              <v-btn class="app" variant="text" href="https://webfollow.cc">
                <img src="https://webfollow.cc/favicon.ico" alt="" />
                <div>wf</div>
              </v-btn>
              <v-btn class="app" variant="text" href="https://e9x.fun">
                <img src="https://e9x.fun/assets/images/logos/e9x.png" alt="" />
                <div>e9x</div>
              </v-btn>
            </v-card>
          </v-menu>

          <v-menu>
            <template v-slot:activator="{ props }">
              <v-avatar
                color="primary"
                class="ma-2 cursor"
                size="small"
                v-bind="props"
              >
                u
              </v-avatar>
            </template>

            <v-list min-width="200">
              <v-list-item @click="changeTheme('light')">
                <v-list-item-title>白色</v-list-item-title>
              </v-list-item>
              <v-list-item @click="changeTheme('dark')">
                <v-list-item-title>黑色</v-list-item-title>
              </v-list-item>
              <v-list-item @click="changeTheme('gmail')">
                <v-list-item-title>gmail</v-list-item-title>
              </v-list-item>
              <v-list-item @click="changeTheme('book')">
                <v-list-item-title>book</v-list-item-title>
              </v-list-item>
            </v-list>
          </v-menu>
        </template>
      </v-app-bar>

      <v-navigation-drawer v-model="drawer" temporary>
        <v-list density="compact" nav>
          <v-list-item
            prepend-icon="mdi-folder-outline"
            to="/"
            title="我的笔记"
            value="home"
          ></v-list-item>
          <v-list-item
            prepend-icon="mdi-tag-outline"
            title="标签"
            to="/tags"
            value="tags"
          ></v-list-item>
        </v-list>
      </v-navigation-drawer>

      <v-main style="--v-layout-right: 14px; --v-layout-left: 14px">
        <v-container class="main-warp" ref="container">
          <router-view></router-view>
        </v-container>
      </v-main>
    </v-layout>
  </v-theme-provider>
</template>
<script>
import { useDisplay } from "vuetify";
export default {
  name: "DefaultLayout",
  data: () => ({
    drawer: false,
    value: "",
    theme: "light",
  }),
  setup() {
    const { mobile } = useDisplay();
    return { mobile };
  },
  mounted() {
    this.theme = localStorage.getItem("theme") || "gmail";
  },
  methods: {
    changeTheme(v) {
      this.theme = v;
      localStorage.setItem("theme", v);
    },
  },
};
</script>
<style lang="scss" scoped>
:deep() {
  .v-navigation-drawer {
    // background: none;
    // border-style: none;
    border: 0;
    .v-list {
      padding: 0 16px 0 0;
    }
    .v-list-item {
      grid-template-columns: 20px auto max-content;
      grid-gap: 18px;
      &:hover {
        border-top-right-radius: 18px;
        border-bottom-right-radius: 18px;
      }
    }
    .v-list-item--density-default.v-list-item--one-line {
      padding-inline-start: 26px !important;
    }
    .v-list-item-title {
      font-size: 14px;
      font-weight: 400;
    }
    .v-list-item--density-default.v-list-item--one-line {
      min-height: 32px;
    }
    .v-list-item--active .v-list-item-title {
      font-weight: 400;
    }
    .v-list-item--active {
      // background: rgba(var(--v-theme-surface-variant), 0.1);
      border-top-right-radius: 18px;
      border-bottom-right-radius: 18px;
    }
    .v-list-item--nav {
      padding-inline-start: 23px;
    }
  }
  .v-list-item--active > .v-list-item__overlay,
  .v-list-item[aria-haspopup="menu"][aria-expanded="true"]
    > .v-list-item__overlay {
    opacity: 0;
  }
}
.search {
  border: 1px solid rgba(0, 0, 0, 0);
  padding: 0 5px;
  min-height: 48px;
  background: rgba(var(--v-theme-on-surface-variant), 0.8);
  margin-left: 80px;
  width: 730px;
  border-radius: 24px;
  display: grid;
  grid-template-columns: 40px auto 40px;
  align-content: center;
  align-items: center;
  .search-btn {
    height: 40px;
    line-height: 40px;
    display: flex;
    justify-content: center;
    align-items: center;
    &:hover {
      background: rgb(var(--v-theme-on-surface-variant));
      border-radius: 50%;
      cursor: pointer;
    }
  }
  svg {
    fill: #666;
  }
  input {
    outline-width: 0;
    padding: 0 10px;
  }
  &:hover {
    border: 1px solid rgb(var(--v-theme-on-surface-variant));
    background: rgb(var(--v-theme-background));
    box-shadow: 0px 2px 2px rgb(var(--v-theme-on-surface-variant));
  }
}
.logo {
  height: 30px;
}
.logo-warp {
  display: flex;
  justify-items: center;
  align-items: center;
}
.apps {
  width: 300px;
  min-height: 300px;
  padding: 2rem 1rem;
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  grid-gap: 1rem;
  text-align: center;
  justify-items: center;
  .app {
    font-size: 0.8rem;
    font-weight: normal;
    text-align: center;
    img {
      width: 32px;
      height: 32px;
    }
  }
  :deep() {
    .v-btn {
      height: 64px;
      width: 64px;
      text-transform: none;
      border-radius: 1rem;
      .v-btn__content {
        display: block;
      }
    }
  }
}
</style>
