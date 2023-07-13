export function mdx2md(str, options = {
  fun: {
    tag
  }
}) {
  return str.split('\n').map((line, index) => {
    let tmpLine = line.trim()
    if (tmpLine.indexOf('!(') === 0) {
      const funName = tmpLine.substr(2, tmpLine.indexOf(')') - 2)
      const args = tmpLine.substr(tmpLine.indexOf('[') + 1, tmpLine.indexOf(']') - tmpLine.indexOf('[') - 1)
      if (options.fun[funName]) {
        return options.fun[funName](args, index)
      }
      return line
    } else {
      return line
    }
  }).join('\n')
}



function tag(str) {
  return ' tags: ' + str.split(',').map((item) => {
    return `[${item}](/?tag=${item})`
  }).join('\n')
}
