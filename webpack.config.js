const path = require("path");
const ESLintPlugin = require("eslint-webpack-plugin");

module.exports = {
	devtool: "inline-source-map",
	mode: "development",
	plugins: [new ESLintPlugin({
		fix: true,
		extensions: [
			"js", "jsx", "ts", "tsx"
		],
		formatter: "stylish"
	})],
	module: {
		rules: [
			{
				test: /\.tsx?$/,
				use: "ts-loader",
				exclude: /node_modules/,
			},
		],
	},
	resolve: {
		extensions: [".tsx", ".ts", ".js"],
	},
	entry: {
		index: "./typescript/index.tsx"
	},
	output: {
		filename: "[name].js",
		path: path.resolve(__dirname, "public/scripts"),
	},
};