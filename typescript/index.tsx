import * as React from "react";
import * as ReactDOM from "react-dom";
import * as MUI from "@mui/material";
import * as Icons from "@mui/icons-material";
import { RestClient } from "./libraries/ReatClient";
import { AcceptHttpHeader } from "./libraries/AcceptHttpHeader";
import { JsonMimeType } from "./libraries/JsonMimeType";
import { ContentTypeHttpHeader } from "./libraries/ContentTypeHttpHeader";

interface Properties {}
interface State {
	userName: string;
	password: string;
}

class IndexPage extends React.Component<Properties, State> {
	public constructor(props: Properties) {
		super(props);

		this.state = { 
			userName: "", 
			password: ""
		};
	}

	private theme = MUI.createTheme();

	render() {
		return (
			<MUI.ThemeProvider theme={this.theme}>
				<MUI.Container component="main" maxWidth="xs">
					<MUI.CssBaseline />
					<MUI.Box sx={{ marginTop: 8, display: "flex", flexDirection: "column", alignItems: "center", }} >
						<MUI.Avatar sx={{ m: 1, bgcolor: "secondary.main", width: "4em", height: "4em" }}>
							<Icons.LockOutlined sx={{ width: "2em", height: "2em" }} />
						</MUI.Avatar>
						<MUI.Typography component="h1" variant="h5">Sign in</MUI.Typography>
						<MUI.TextField margin="normal" required fullWidth id="email" label="Email Address" name="email" autoComplete="email" autoFocus 
							onChange={this.userChanged.bind(this)} />
						<MUI.TextField margin="normal" required fullWidth name="password" label="Password" type="password" id="password" autoComplete="current-password" 
							onChange={this.passwordChanged.bind(this)} />
						<MUI.Button fullWidth variant="contained" sx={{ mt: 3, mb: 2 }} onClick={this.signInClicked.bind(this)} >Sign In</MUI.Button>
						<MUI.Grid container justifyContent="center">
							<MUI.Grid item><MUI.Link href="#" variant="body2">Reset Password</MUI.Link></MUI.Grid>
						</MUI.Grid>
					</MUI.Box>
					<MUI.Typography variant="body2" color="text.secondary" align="center" sx={{ mt: 8, mb: 4 }} >
						Copyright &copy; Lago Vista Technologies LLC {new Date().getFullYear()}
					</MUI.Typography>
				</MUI.Container>
			</MUI.ThemeProvider>
		);
	}

	private userChanged(ev: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
		this.setState({ userName: ev.target.value });
	}	
	private passwordChanged(ev: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) {
		this.setState({ password: ev.target.value });
	}
	private signInClicked() {
		const rc = new RestClient();
		rc.url = "/api/v1/session/login";
		rc.headers.push(new AcceptHttpHeader(new JsonMimeType()));
		rc.headers.push(new ContentTypeHttpHeader(new JsonMimeType()));
		rc.method = "POST";
		rc.send({
			"userName": this.state.userName, 
			"password": this.state.password
		})
			.then((xhr: XMLHttpRequest) => {
				console.log(xhr);
				return;
			});
	}
}

window.onload = function() {
	const elm = document.getElementById("root");
	ReactDOM.render(<IndexPage />, elm);
};