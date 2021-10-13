import { HttpHeader } from "./HttpHeader";
import { MimeType } from "./MimeType";

export class ContentTypeHttpHeader implements HttpHeader {
	private _mimetype: MimeType;

	public constructor(mimetype: MimeType) {
		this._mimetype = mimetype;
	}
	
	public get name(): string { return "Content-Type"; }
	public get value(): string { return this._mimetype.name; }
	public get mimeType(): MimeType { return this._mimetype; }
}