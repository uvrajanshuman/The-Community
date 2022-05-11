export class CommentModel{
    id!:number;
    postId!: number;
    userName!:string;
    text!: string;
    isAnswer!:boolean;//"false"
    createdDate?:string;
}

