import {
  ref
} from 'vue'

export default function useRequest(request) {
  const isFinished = ref(false)
  const isFailed = ref(false)
  const data = ref({})
  const execute = async (...args) => {
    isFinished.value = false
    isFailed.value = false
    data.value = {}
    try {
      data.value = await request(...args)
    } catch (e) {
      isFailed.value = true
    } finally {
      isFinished.value = true
    }
  }
  return {
    isFinished,
    isFailed,
    data,
    execute
  }
}
