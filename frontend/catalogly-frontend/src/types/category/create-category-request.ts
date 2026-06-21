export interface CreateCategoryRequestDTO{
    name:string;
    description:string;
    items?:string[] | null
}