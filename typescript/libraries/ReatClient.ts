/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @typescript-eslint/no-unused-vars */
/* eslint-disable @typescript-eslint/explicit-module-boundary-types */
export class RestClient {
	public url: string;
	public headers: { [key: string]: string };
	public method: "GET" | "POST" | "PUT";

	public onloadend: ((this: XMLHttpRequest, ev: ProgressEvent) => any) | null = null;
	public onloadstart: ((this: XMLHttpRequest, ev: ProgressEvent) => any) | null = null;
	public onprogress: ((this: XMLHttpRequest, ev: ProgressEvent) => any) | null = null;
	public onreadystatechange: ((this: XMLHttpRequest, ev: ProgressEvent) => any) | null = null;

	public send(body?: Document | XMLHttpRequestBodyInit | null): Promise<XMLHttpRequest> {
		return new Promise<any>((resolve, reject) => {
			const xhr = new XMLHttpRequest();
			const success = (ev: ProgressEvent) => { resolve(xhr); };
			const failure = (ev: ProgressEvent) => { reject(xhr); };
			
			xhr.onabort = failure;
			xhr.onerror = failure;
			xhr.onload = success;
			xhr.ontimeout = failure;
		
			xhr.onloadend = this.onloadend;
			xhr.onloadstart = this.onloadstart;
			xhr.onprogress = this.onprogress;
			xhr.onreadystatechange = this.onreadystatechange;
			
			Object.keys(this.headers).forEach((value: string) => {
				xhr.setRequestHeader(value, this.headers[value]);
			});

			xhr.open(this.method, this.url);
			xhr.send(body);
		});
	}
}