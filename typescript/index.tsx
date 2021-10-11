import * as React from "react";
import * as ReactDOM from "react-dom";
import * as MUI from "@mui/material";
import * as Icons from "@mui/icons-material";

interface Properties {}
interface State {}

class IndexPage extends React.Component<Properties, State> {
	theme = MUI.createTheme();
	handleSubmit = () => { return; };

	render() {
		return (
			<MUI.ThemeProvider theme={this.theme}>
				<MUI.Container component="main" maxWidth="xs">
					<MUI.CssBaseline />
					<MUI.Box sx={{ marginTop: 8, display: "flex", flexDirection: "column", alignItems: "center", }} >
						<MUI.Avatar sx={{ m: 1, bgcolor: "secondary.main" }}>
							<Icons.LockOutlined />
						</MUI.Avatar>
						<MUI.Typography component="h1" variant="h5">Sign in</MUI.Typography>
						<MUI.Box component="form" onSubmit={this.handleSubmit} noValidate sx={{ mt: 1 }}>
							<MUI.TextField margin="normal" required fullWidth id="email" label="Email Address" name="email" autoComplete="email" autoFocus />
							<MUI.TextField margin="normal" required fullWidth name="password" label="Password" type="password" id="password" autoComplete="current-password" />
							<MUI.Button type="submit" fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} >Sign In</MUI.Button>
							<MUI.Grid container>
								<MUI.Grid item xs><MUI.Link href="#" variant="body2">Forgot password?</MUI.Link></MUI.Grid>
								<MUI.Grid item><MUI.Link href="#" variant="body2">Don&apos;t have an account? Sign Up</MUI.Link></MUI.Grid>
							</MUI.Grid>
						</MUI.Box>
					</MUI.Box>
					<MUI.Typography variant="body2" color="text.secondary" align="center" sx={{ mt: 8, mb: 4 }} >
						Copyright &copy;
						<MUI.Link color="inherit" href="https://material-ui.com/">Your Website</MUI.Link>{" "}
						{new Date().getFullYear()}{"."}
					</MUI.Typography>
				</MUI.Container>
			</MUI.ThemeProvider>
		);
	}
}

window.onload = function() {
	const elm = document.getElementById("root");
	ReactDOM.render(<IndexPage />, elm);
};