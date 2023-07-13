<template>
  <div class="contrl-wap" :class="{ 'contrl-warp-fullscreen': isFullscreen }">
    <div class="tool">
      <v-icon @click="$router.back(-1)">mdi-chevron-left</v-icon>
      <v-icon @click="toggleFullscreen">{{
        isFullscreen ? "mdi-fullscreen-exit" : "mdi-fullscreen"
      }}</v-icon>
    </div>
    <div class="grid" :class="{ col2 }">
      <div class="textarea-warp">
        <textarea v-model="markdown" cols="50" class="control"></textarea>
        <v-progress-circular
          v-show="submitLoading"
          class="save-progress"
          indeterminate
          color="primary"
        ></v-progress-circular>
        <v-icon class="view-toggle" size="18" @click="col2 = !col2">{{
          col2 ? "mdi-arrow-collapse-right" : "mdi-arrow-collapse-left"
        }}</v-icon>
      </div>
      <div class="control" v-if="col2">
        <div v-html="markdownToHtml"></div>
      </div>
    </div>
  </div>
  <v-snackbar v-model="error.snackbar" multi-line>
    {{ error.text }}
  </v-snackbar>
</template>

<script>
import hljs from "highlight.js";
import "highlight.js/styles/github.css";
import { marked } from "marked";
import { mdx2md } from "@/utils/mdx";
import { debounce } from "lodash";
import { save, update, get } from "@/api/index";
import useScreen from "@/use/screen";
import { onMounted, ref } from "vue";
export default {
  props: {
    id: {
      type: String,
      default: "",
    },
  },
  data() {
    return {
      markdown:
        "# Hello, Mrico Note \n!(tag)[tag1,tag2] \n ## Edit your markdown here",
      clientHeight: 0,
      submitLoading: false,
      autoSubmit: false,
      error: {
        snackbar: false,
        text: "",
      },
      col2: true,
    };
  },
  setup() {
    const containerRef = ref(null);
    onMounted(() => {
      containerRef.value = document.querySelector(".main-warp");
    });
    const { isFullscreen, toggleFullscreen } = useScreen(containerRef);
    return {
      isFullscreen,
      toggleFullscreen,
    };
  },
  methods: {
    submit: debounce(async function () {
      this.submitLoading = true;
      let tags = [];
      mdx2md(this.markdown, {
        fun: {
          tag: (str) => {
            tags = str.split(",").map((item) => item.trim());
          },
        },
      });
      const data = {
        title: this.markdown.split("\n")[0].replace("#", "").trim(),
        content: this.markdown,
        tags,
      };
      try {
        if (this.id) {
          await update(this.id, data);
        } else {
          let res = await save(data);
          this.$router.replace({ path: `/d/${res.id}` });
        }
      } catch (e) {
        this.submitLoading = false;
        this.error.snackbar = true;
        this.error.text = e.data.message;
      }
      setTimeout(() => {
        this.submitLoading = false;
      }, 2000);
    }, 5000),
  },
  watch: {
    markdown() {
      this.calClientHeight();
      if (this.autoSubmit) {
        this.submit();
      }
    },
  },
  computed: {
    markdownToHtml() {
      const md = mdx2md(this.markdown);
      return marked(md, {
        gfm: true,
        pedantic: false,
        sanitize: false,
        tables: true,
        breaks: false,
        smartLists: true,
        smartypants: false,
        highlight: function (code) {
          return hljs.highlightAuto(code).value;
        },
      });
    },
    height() {
      return this.clientHeight - 105 + "px";
    },
  },
  async mounted() {
    this.calClientHeight = debounce(() => {
      this.$nextTick(() => {
        this.clientHeight = document.body.clientHeight;
      });
    }, 500);
    if (this.id) {
      this.markdown = (await get(this.id)).content;
    }
    setTimeout(() => {
      this.autoSubmit = true;
    }, 1000);
  },
};
</script>
<style lang="scss" scoped>
.contrl-wap {
  height: calc(100vh - 3rem - 64px);
}
.contrl-warp-fullscreen {
  height: calc(100vh - 2rem);
}
.grid {
  height: calc(100% - 2rem - 10px);
  display: grid;
  grid-template-columns: 1fr;
  grid-gap: 1rem;
}
.col2 {
  grid-template-columns: 1fr 1fr;
}
.tool {
  margin-bottom: 1rem;
  display: flex;
  justify-content: space-between;
}
.control {
  width: 100%;
  height: 100%;
  padding: 1rem;
  box-sizing: border-box;
  resize: none;
  overflow: auto;
}
.textarea-warp {
  position: relative;
  .control {
    background: rgba(var(--v-theme-code), 0.6);
  }
}
.save-progress {
  position: absolute;
  right: 1rem;
  bottom: 1rem;
}
.view-toggle {
  position: absolute;
  top: 1rem;
  right: 1rem;
}
@media screen and (max-width: 768px) {
  .grid {
    display: grid;
    grid-template-columns: 1fr;
    grid-gap: 1rem;
  }
  .control {
    // width: calc(100vw - 2rem);
  }
}
</style>
