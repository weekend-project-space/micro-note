import {
  ref,
  onMounted,
  onUnmounted
} from 'vue'

export default function useScreen(el) {
  const isFullscreen = ref(false)

  function reloadFullScreen() {
    isFullscreen.value = isFullscreen0(el.value)
  }
  onMounted(() => {
    reloadFullScreen()
    el.value.addEventListener("fullscreenchange", reloadFullScreen);
  })
  onUnmounted(() => {
    el.value.removeEventListener("fullscreenchange", reloadFullScreen);
  })
  const toggle = () => {
    if (isFullscreen.value) {
      exitFullscreen(el.value)
    } else {
      fullscreen(el.value)
    }
  }
  return {
    isFullscreen,
    toggleFullscreen: toggle
  }
}


function fullscreen(ele) {
  if (ele.requestFullscreen) {
    ele.requestFullscreen();
  } else if (ele.mozRequestFullScreen) {
    ele.mozRequestFullScreen();
  } else if (ele.webkitRequestFullscreen) {
    ele.webkitRequestFullscreen();
  } else if (ele.msRequestFullscreen) {
    ele.msRequestFullscreen();
  }
}

function exitFullscreen(ele) {
  if (document.exitFullScreen) {
    document.exitFullScreen();
  } else if (document.mozCancelFullScreen) {
    document.mozCancelFullScreen();
  } else if (document.webkitExitFullscreen) {
    document.webkitExitFullscreen();
  } else if (ele.msExitFullscreen) {
    ele.msExitFullscreen();
  }
}

function isFullscreen0(el) {
  return document.fullscreenElement || document.msFullscreenElement || document.mozFullScreenElement || document.webkitFullscreenElement || el.fullscreenElement || false;
}
