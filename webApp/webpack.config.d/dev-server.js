// Disable the red error overlay in the webpack dev server.
// "Dispatching key event while focus system is invalidated" is a known
// CMP 1.7.0 JS-canvas race condition on first keyboard event — it does
// NOT prevent rendering and is safe to silence in development.
if (config.devServer) {
    config.devServer.client = Object.assign(
        config.devServer.client || {},
        { overlay: false }
    );
}
