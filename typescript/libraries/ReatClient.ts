/* eslint-disable @typescript-eslint/no-explicit-any */
/* eslint-disable @typescript-eslint/no-unused-vars */

import { HttpHeader } from "./HttpHeader";

/* eslint-disable @typescript-eslint/explicit-module-boundary-types */
export class RestClient {
	public url: string;
	public headers: HttpHeader[] = [];
	public method: "GET" | "HEAD" | "POST" | "PUT" | "DELETE" | "CONNECT" | "OPTIONS" | "TRACE";

	public onloadend: ((this: XMLHttpRequest, ev: ProgressEvent) => any) | null = null;
	public onloadstart: ((this: XMLHttpRequest, ev: ProgressEvent) => any) | null = null;
	public onprogress: ((this: XMLHttpRequest, ev: ProgressEvent) => any) | null = null;
	public onreadystatechange: ((this: XMLHttpRequest, ev: ProgressEvent) => any) | null = null;

	public send(body?: any): Promise<XMLHttpRequest> {
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
			
			xhr.open(this.method, this.url);
			this.headers.forEach((header: HttpHeader) => {
				xhr.setRequestHeader(header.name, header.value);
			});
			if(body)
				xhr.send(JSON.stringify(body));
			else
				xhr.send();
		});
	}
}