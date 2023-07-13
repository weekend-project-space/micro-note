<template>
  <v-chip-group>
    <v-chip
      v-for="tag in data"
      :key="tag"
      @click="$router.push('/?tag=' + tag)"
      >{{ tag }}</v-chip
    >
  </v-chip-group>
</template>
<script setup>
import { ref , onMounted} from "vue";
import { listAll } from "@/api/tags";

const data = ref([]);

onMounted(async () => {
  data.value = await listAll();
});
</script>
