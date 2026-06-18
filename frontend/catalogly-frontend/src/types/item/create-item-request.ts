export interface CreateItemRequestDTO {
  categoryId?: string | null; 
  name: string;
  about: string;
  price: string;
  stock: number;
}