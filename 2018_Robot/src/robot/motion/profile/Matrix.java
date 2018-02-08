package robot.motion.profile;

public class Matrix {

	private double[][] data;
	private int m, n;

	public Matrix(int m, int n) {
		this.m = m;
		this.n = n;
		data = new double[m][n];
	}

	public Matrix(double[][] data) {
		this.data = data;

		this.m = data.length;
		this.n = data[0].length;
	}

	private Matrix(Matrix m) {
		this(m.data);
	}

	public Matrix transpose() {
		Matrix A = new Matrix(n, m);
		for (int i = 0; i < m; i++)
			for (int j = 0; j < n; j++)
				A.data[j][i] = this.data[i][j];
		return A;
	}

	private void swap(int i, int j) {
		double[] temp = data[i];
		data[i] = data[j];
		data[j] = temp;
	}

	public Matrix solve(Matrix rhs) {

		if (m != n || rhs.m != n || rhs.n != 1)
			throw new RuntimeException("Illegal matrix dimensions.");

		// create copies of the data
		Matrix A = new Matrix(this);
		Matrix b = new Matrix(rhs);

		// A.show();

		// Gaussian elimination with partial pivoting
		for (int i = 0; i < n; i++) {

			// find pivot row and swap
			int max = i;
			for (int j = i + 1; j < n; j++)
				if (Math.abs(A.data[j][i]) > Math.abs(A.data[max][i]))
					max = j;
			A.swap(i, max);
			b.swap(i, max);

			// singular
			if (A.data[i][i] == 0.0)
				throw new RuntimeException("Matrix is singular.");

			// pivot within b
			for (int j = i + 1; j < n; j++)
				b.data[j][0] -= b.data[i][0] * A.data[j][i] / A.data[i][i];

			// pivot within A
			for (int j = i + 1; j < n; j++) {
				double m = A.data[j][i] / A.data[i][i];
				for (int k = i + 1; k < n; k++) {
					A.data[j][k] -= A.data[i][k] * m;
				}
				A.data[j][i] = 0.0;
			}
		}

		// back substitution
		Matrix x = new Matrix(n, 1);
		for (int j = n - 1; j >= 0; j--) {
			double t = 0.0;
			for (int k = j + 1; k < n; k++)
				t += A.data[j][k] * x.data[k][0];
			x.data[j][0] = (b.data[j][0] - t) / A.data[j][j];
		}
		return x;
	}

	public void show() {
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(data[i][j] + " ");
			}
			System.out.println();
		}
	}

	public double[][] getData() {
		return this.data;
	}

}
