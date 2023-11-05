import { defineConfig } from "vite";
import scalaJSPlugin from "@scala-js/vite-plugin-scalajs";

export default defineConfig({
    plugins: [scalaJSPlugin({
        projectID: 'clientJS', // sbt-scalajs-crossproject adds JS suffix
    })],
    server: {
        proxy: {
            '^/io\.github\.windymelt\.airframeexercise\.api\.v1\.MyService/.*': 'http://localhost:8080',
        }
    },
});
