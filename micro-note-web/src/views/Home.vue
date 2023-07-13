<template>
  <v-row>
    <v-col v-if="!Object.keys(route.query).length" cols="auto">
      <router-link to="/d/create">
        <v-card class="item">
          <div class="bg">
            <v-icon size="55">mdi:mdi-file-document-plus-outline</v-icon>
          </div>
          <div class="footer">
            <div class="title">添加</div>
            <div>&nbsp;</div>
          </div>
        </v-card>
      </router-link>
    </v-col>
    <v-col v-for="item in data" :key="item.id" cols="auto">
      <v-card class="item" @click="$router.push('/d/' + item.id)">
        <div class="bg">
          <v-icon size="55">mdi:mdi-file-document-outline</v-icon>
        </div>
        <div class="footer">
          <div class="title" v-text="item.title" :title="item.title"></div>
          <div class="action">
            <small v-text="item.time"></small>
            <v-menu @click.stop="() => {}">
              <template v-slot:activator="{ props }">
                <v-btn
                  icon="mdi-dots-vertical"
                  variant="text"
                  size="x-small"
                  v-bind="props"
                >
                </v-btn>
              </template>
              <v-list>
                <v-list-item @click="delRow(item)">
                  <template v-slot:prepend>
                    <v-icon>mdi-delete-outline</v-icon>
                  </template>
                  <v-list-item-title>删除</v-list-item-title>
                </v-list-item>
                <v-list-item @click="openNewTab(item)">
                  <template v-slot:prepend>
                    <v-icon>mdi-open-in-new</v-icon>
                  </template>
                  <v-list-item-title>新标签打开</v-list-item-title>
                </v-list-item>
              </v-list>
            </v-menu>
          </div>
        </div>
      </v-card>
    </v-col>
  </v-row>

  <v-dialog v-model="dialog" width="360">
    <v-card>
      <v-card-text class="text-center"> 确认删除? </v-card-text>
      <v-card-actions>
        <v-btn color="primary" block @click="delAction">确定</v-btn>
      </v-card-actions>
    </v-card>
  </v-dialog>
</template>
<script setup>
import { findAll, del } from "@/api/index";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
const data = ref([]);
const loading = ref(false);
const route = useRoute();
const dialog = ref(false);
const row = ref({});
async function loadData() {
  loading.value = true;
  const res = await findAll(route.query);
  data.value = [...data.value, ...res.content];
  loading.value = false;
}

async function reloadData() {
  data.value = [];
  await loadData();
}

function delRow(item) {
  dialog.value = true;
  row.value = item;
}

async function delAction() {
  await del(row.value.id);
  reloadData();
  dialog.value = false;
}

function openNewTab(item) {
  window.open("/d/" + item.id);
}

onMounted(async () => {
  loadData();
});

watch(route, () => {
  reloadData();
});
</script>
<style lang="scss" scoped>
:deep() {
  .v-list-item__prepend > .v-icon {
    margin-inline-end: 1rem;
  }
  .v-list-item-title {
    font-size: 0.8rem;
  }
}
.item {
  position: relative;
  width: 145px;
  height: 200px;
  .title {
    //2行多出省略号
    // word-break: break-all;
    // display: -webkit-box;
    // -webkit-box-orient: vertical;
    // -webkit-line-clamp: 2;
    // text-overflow: ellipsis;
    // overflow: hidden;
    font-size: 0.8rem;
    //超出省略号
    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
  }
  .footer {
    position: absolute;
    bottom: 0;
    left: 0;
    right: 0;
    padding: 0.5rem 0.5rem 0.5rem 0.8rem;
  }
  .action {
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .bg {
    // border-radius: 1rem;
    background: rgb(var(--v-theme-code));
    height: 130px;
    display: flex;
    align-items: center;
    justify-content: center;
    > * {
      opacity: 0.8;
    }
  }
}
</style>
