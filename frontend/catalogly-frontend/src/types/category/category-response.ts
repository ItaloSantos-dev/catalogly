import { ItemResponseDTO } from "../item/item-response";

export interface CategoryRespondeDTO{
    id:string;
    name: string;
    description:string;
    itemsCount:number;
    items:ItemResponseDTO[]
}

