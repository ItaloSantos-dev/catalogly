import { Item } from "./items";

export interface CreateStockOrderRequestDTO {
  supplierId: string;
  items: Item[];
}