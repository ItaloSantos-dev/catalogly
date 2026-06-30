import { UpdateImageItem } from "./update-image-item";

export interface UpdateItemRequestDTO {
  categoryId:string;
  name: string;
  about: string;
  price: string;
  stock: number;
  updateImages:boolean[]|null;
}