export class PostModel {
    id!: number;
    postName!: string;
    url!: string;
    description!: string;
    voteCount!: number;
    userName!: string;
    productName!: string;
    commentCount!: number;
    upVote?: boolean;
    downVote?: boolean;
    createdDate!:string;
}