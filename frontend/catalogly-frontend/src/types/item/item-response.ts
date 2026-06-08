export interface ItemResponseDTO {
  id: string; // UUID
  categoryName: string;
  name: string;
  about: string;
  price: number;
  stock: number;
  deleted: boolean;
  imagePath1: string | null;
  imagePath2: string | null;
  imagePath3: string | null;
}