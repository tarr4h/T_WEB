const {createProxyMiddleware} = require('http-proxy-middleware')


module.exports = app => {

    const urlPaths = ['/comn', '/pack', '/awsMas'];

    app.use(
        createProxyMiddleware(urlPaths, {
            // target: 'https://tarr4h.com',
            target: 'http://localhost:8080',
            // target: 'http://hansolcnc.co.kr',
            changeOrigin: true,
        })
    )
}