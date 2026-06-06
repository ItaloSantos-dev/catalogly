import { TopItem } from './top-item';
import { RecentOrder } from './recent-order';
import { CategorySummary } from './category-summary';

export interface CatalogDashboard {
  catalogId: string;
  name: string;
  slug: string;
  totalItems: number;
  totalCategories: number;
  totalOrders: number;
  completedOrders: number;
  pendingOrders: number;
  totalRevenue: number;
  averageOrderValue: number;
  stockValue: number;
  lowStockItems: number;
  outOfStockItems: number;
  activeCoupons: number;
  pendingInvoices: number;
  topItems: TopItem[];
  recentOrders: RecentOrder[];
  categories: CategorySummary[];
}
