package po;

public class MapOrderProductPo extends MapOrderProduct {
	 public Product getProduct() {
			return product;
		}

		public void setProduct(Product product) {
			this.product = product;
		}

		private Product product;
}
