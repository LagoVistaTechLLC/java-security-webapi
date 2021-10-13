import { MimeType } from "./MimeType";

export class JsonMimeType implements MimeType {
	public get name(): string { return "application/json"; }
	public get extension(): string { return "json"; }
}