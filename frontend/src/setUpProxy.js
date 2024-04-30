const {createProxyMiddleware} = require('http-proxy-middleware')


module.exports = app => {

    const urlPaths = ['/comn', '/pack'];

    app.use(
        createProxyMiddleware(urlPaths, {
            target: 'http://localhost:8083',
            changeOrigin: true,
        })
    )
}