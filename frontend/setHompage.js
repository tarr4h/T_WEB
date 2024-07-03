const fs = require('fs')
const { REACT_APP_HOMEPAGE_URL } = process.env

const data = JSON.parse(fs.readFileSync('package.json', 'utf-8'))
data.homepage = REACT_APP_HOMEPAGE_URL
fs.writeFileSync('package.json', JSON.stringify(data, null, 2))